import streamlit as st
from api_client import get_booking_by_id, perform_check_in

st.set_page_config(page_title="My Booking", page_icon="🎫", layout="centered")

st.title("🎫 My Booking & Check-in")

# --- Section to display the most recent booking confirmation ---
if 'booking_confirmation' in st.session_state and st.session_state.booking_confirmation:
    conf = st.session_state.booking_confirmation
    st.success(f"Booking Confirmed! Your Booking ID is: **{conf.get('bookingId')}**")
    with st.expander("View Confirmation Details", expanded=True):
        st.json(conf)
    st.markdown("---")


# --- Section to find a booking by ID ---
st.header("Find Your Booking")
booking_id_input = st.number_input(
    "Enter your Booking ID", 
    min_value=1, 
    step=1, 
    key="booking_id_input"
)

if st.button("Find Booking"):
    if booking_id_input:
        with st.spinner(f"Searching for booking {booking_id_input}..."):
            booking_details = get_booking_by_id(booking_id_input)
            st.session_state['retrieved_booking'] = booking_details
    else:
        st.warning("Please enter a Booking ID.")

# --- Display booking details and check-in option ---
if 'retrieved_booking' in st.session_state and st.session_state.retrieved_booking:
    booking = st.session_state.retrieved_booking
    
    st.subheader("Booking Details")
    with st.container(border=True):
        # NOTE: Adjust keys based on your actual Booking entity returned by getBookingById
        status = booking.get('status', 'N/A').upper()
        booking_id = booking.get('id', 'N/A')
        flight_id = booking.get('flightId', 'N/A')

        st.markdown(f"**Booking ID:** `{booking_id}`")
        st.markdown(f"**Flight ID:** `{flight_id}`")
        st.markdown(f"**Status:** `{status}`")

        # --- Check-in Button ---
        # The button is only shown if the booking is confirmed and not yet checked-in
        if status == 'CONFIRMED':
            if st.button("✈️ Check-in Now", type="primary"):
                with st.spinner("Checking you in..."):
                    checkin_response = perform_check_in(booking_id)
                    if checkin_response:
                        st.session_state['checkin_details'] = checkin_response
                        # Refresh the booking details to show updated status
                        st.session_state['retrieved_booking'] = get_booking_by_id(booking_id)
                        st.rerun()
        
        elif status == 'CHECKED_IN':
            st.info("You are already checked in for this flight.")
        
        else:
            st.warning(f"This booking cannot be checked in (Status: {status}).")

# --- Display Boarding Pass after successful check-in ---
if 'checkin_details' in st.session_state and st.session_state.checkin_details:
    details = st.session_state.checkin_details
    # Ensure it's for the booking currently being viewed
    if details.get('bookingId') == st.session_state.get('retrieved_booking', {}).get('id'):
        st.header(" boarding pass")
        st.success(details.get('message'))
        with st.container(border=True):
            col1, col2 = st.columns(2)
            with col1:
                st.markdown("**Booking ID**")
                st.code(details.get('bookingId'))
            with col2:
                st.markdown("**Seat Number**")
                st.code(details.get('seatNumber'))
        # Clear after showing to avoid showing it for a different booking later
        if st.button("Clear Boarding Pass"):
            del st.session_state['checkin_details']
            st.rerun()