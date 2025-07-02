import streamlit as st
from api_client import create_customer, create_booking

st.set_page_config(page_title="Book Flight", page_icon="🛒", layout="centered")

st.title("🛒 Complete Your Booking")

# Check if a flight has been selected from the search page
if 'selected_flight' not in st.session_state or not st.session_state.selected_flight:
    st.warning("Please search for and select a flight on the 'Search Flights' page first.")
    st.page_link("Search_Flights.py", label="Go to Search", icon="✈️")
    st.stop()

# Display the details of the selected flight
flight = st.session_state.selected_flight
st.subheader("Your Selected Flight")
with st.container(border=True):
    # NOTE: Adjust these keys if your FlightSearchResultDTO uses different names
    flight_id = flight.get('flightId') 
    flight_number = flight.get('flightNumber', 'N/A')
    origin = flight.get('origin', 'N/A')
    destination = flight.get('destination', 'N/A')
    price = flight.get('price', 0.0)

    st.markdown(f"**Flight:** {flight_number}")
    st.markdown(f"**Route:** {origin} → {destination}")
    st.metric("Total Price", f"${price:,.2f}")

if not flight_id:
    st.error("Error: The selected flight is missing a flight ID. Cannot proceed with booking.")
    st.stop()

# --- Passenger Information Form ---
st.subheader("Passenger Details")
with st.form("booking_form"):
    name = st.text_input("Full Name")
    email = st.text_input("Email Address")
    submitted = st.form_submit_button("Confirm and Book")

    if submitted:
        if not name or not email:
            st.error("Please fill in all passenger details.")
        else:
            with st.spinner("Processing your booking..."):
                # 1. Create the customer
                customer_data = create_customer(name, email)
                
                if customer_data:
                    customer_id = customer_data.get("id")
                    
                    # 2. Create the booking with the new customer ID
                    booking_data = create_booking(flight_id, customer_id)

                    if booking_data:
                        st.session_state['booking_confirmation'] = booking_data
                        # Clear the selected flight to prevent re-booking
                        st.session_state['selected_flight'] = None 
                        st.success("Booking successful!")
                        st.balloons()
                        st.info("You can now view your booking in 'My Booking'.")
                        # You can use st.switch_page in Streamlit 1.33+
                        # st.switch_page("pages/3_🎫_My_Booking.py")
                        st.rerun() # Rerun to clear the form and show success message