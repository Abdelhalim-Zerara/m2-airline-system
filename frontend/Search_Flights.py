import streamlit as st
from api_client import search_flights

st.set_page_config(
    page_title="M2 Airlines - Search Flights",
    page_icon="✈️",
    layout="wide"
)

# Initialize session state variables if they don't exist
if 'search_results' not in st.session_state:
    st.session_state['search_results'] = None
if 'selected_flight' not in st.session_state:
    st.session_state['selected_flight'] = None

st.title("✈️ Find Your Next Flight")

# --- Search Form ---
with st.form("search_form"):
    col1, col2 = st.columns(2)
    with col1:
        origin = st.text_input("From (e.g., Paris)", "Paris")
    with col2:
        destination = st.text_input("To (e.g., New York)", "New York")
    
    # In a real app, you would add a date picker
    # date = st.date_input("Departure Date")

    submitted = st.form_submit_button("Search Flights")
    if submitted:
        if not origin or not destination:
            st.warning("Please enter both origin and destination.")
        else:
            with st.spinner("Searching for the best flights..."):
                results = search_flights(origin, destination)
                st.session_state['search_results'] = results

# --- Display Results ---
if st.session_state['search_results']:
    st.subheader("Search Results")
    results = st.session_state['search_results']
    
    if not results:
        st.info("No flights found for the selected route. Please try another search.")
    else:
        for flight in results:
            # You might need to adjust the keys based on your actual FlightSearchResultDTO
            flight_number = flight.get('flightNumber', 'N/A')
            price = flight.get('price', 0.0)
            dep_time = flight.get('departureTime', 'N/A')
            arr_time = flight.get('arrivalTime', 'N/A')

            with st.container(border=True):
                col1, col2, col3 = st.columns([2, 2, 1])
                with col1:
                    st.markdown(f"**Flight {flight_number}**")
                    st.text(f"{flight.get('origin', 'N/A')} → {flight.get('destination', 'N/A')}")
                with col2:
                    st.text(f"Depart: {dep_time}")
                    st.text(f"Arrive: {arr_time}")
                with col3:
                    st.metric("Price", f"${price:,.2f}")
                    # The button's key is unique to handle multiple results
                    if st.button("Select this flight", key=f"select_{flight_number}"):
                        st.session_state['selected_flight'] = flight
                        st.success(f"Flight {flight_number} selected!")
                        # We can add st.switch_page("pages/2_🛒_Book_Flight.py") with Streamlit 1.33+
                        st.info("Proceed to the 'Book Flight' page to complete your reservation.")
                        st.rerun()

elif st.session_state['search_results'] is not None: # Handle case where search returned empty list
    st.info("No flights found for the selected route. Please try another search.")

# --- Show selected flight ---
if st.session_state['selected_flight']:
    st.sidebar.subheader("Your Selected Flight")
    selected = st.session_state['selected_flight']
    st.sidebar.markdown(f"**{selected.get('flightNumber')}**")
    st.sidebar.text(f"{selected.get('origin')} → {selected.get('destination')}")
    st.sidebar.metric("Price", f"${selected.get('price', 0.0):,.2f}")
    if st.sidebar.button("Clear Selection"):
        st.session_state['selected_flight'] = None
        st.rerun()