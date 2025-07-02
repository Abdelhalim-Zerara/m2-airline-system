import requests
import streamlit as st

# Your API Gateway URL. 
API_GATEWAY_URL = "http://localhost:9090"

def search_flights(origin: str, destination: str):
    """Calls the search-service to find flights."""
    search_url = f"{API_GATEWAY_URL}/search"
    params = {"origin": origin, "destination": destination}
    try:
        response = requests.get(search_url, params=params, timeout=10)
        response.raise_for_status()
        return response.json()
    except requests.exceptions.RequestException as e:
        st.error(f"Error connecting to the search service: {e}")
        return None

def create_customer(name: str, email: str):
    """Calls the customer-service to create a new customer."""
    customer_url = f"{API_GATEWAY_URL}/customers"
    payload = {"name": name, "email": email}
    try:
        response = requests.post(customer_url, json=payload, timeout=10)
        response.raise_for_status()
        return response.json()
    except requests.exceptions.RequestException as e:
        st.error(f"Error creating customer: {e}")
        return None

def create_booking(flight_id: int, customer_id: int):
    """Calls the booking-service to create a new booking."""
    booking_url = f"{API_GATEWAY_URL}/bookings"
    payload = {"flightId": flight_id, "customerId": customer_id}
    try:
        response = requests.post(booking_url, json=payload, timeout=10)
        response.raise_for_status()
        return response.json()
    except requests.exceptions.RequestException as e:
        st.error(f"Error creating booking: {e}")
        return None

def get_booking_by_id(booking_id: int):
    """Calls the booking-service to retrieve a booking by its ID."""
    booking_url = f"{API_GATEWAY_URL}/bookings/{booking_id}"
    try:
        response = requests.get(booking_url, timeout=10)
        response.raise_for_status()
        return response.json()
    except requests.exceptions.RequestException as e:
        # Handle 404 Not Found gracefully
        if e.response and e.response.status_code == 404:
            st.warning(f"Booking with ID {booking_id} not found.")
        else:
            st.error(f"Error retrieving booking: {e}")
        return None

def perform_check_in(booking_id: int):
    """Calls the checkin-service to perform check-in."""
    checkin_url = f"{API_GATEWAY_URL}/checkin"
    payload = {"bookingId": booking_id}
    try:
        response = requests.post(checkin_url, json=payload, timeout=10)
        response.raise_for_status()
        return response.json()
    except requests.exceptions.RequestException as e:
        # Check for specific bad request errors from the service
        if e.response and e.response.status_code == 400:
            error_data = e.response.json()
            st.error(f"Check-in failed: {error_data.get('message', 'Invalid request.')}")
        else:
            st.error(f"Error during check-in: {e}")
        return None