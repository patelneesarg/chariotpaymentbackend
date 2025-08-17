# Payments Application

A payments management Kotlin Spring Boot backend that allows you to 

- List all pending payments
- Filter by scheduled date and/or recipient (e.g., `GET api/payments?after=2025-08-15&recipient=John%20Doe`) returns payments for John Doe with scheduled_date after 2025-08-15

---

## Prerequisites

- **Java 17+**
- **Gradle 8+**
- `payments.json` file placed in `src/main/resources/`

---

## Backend Setup (Kotlin Spring Boot)

### Install Dependencies

From the backend project root:

```bash
./gradlew clean build
```
###Run the server locally
```bash
./gradlew bootRun
```
The server will start on http://localhost:8080

## Usage example
```bash
curl -X GET "http://localhost:8080/api/payments?after=2025-08-15&recipient=John%20Doe"
```
#### Response:

```bash
{
  "payments": [
    {
      "id": "txn_001",
      "amount": 5000.0,
      "currency": "USD",
      "scheduledDate": "2025-08-15",
      "recipient": "John Doe",
      "within24Hours": false
    },
    ...
  ],
  "totalAmount": 5000.0
}
```
## Run tests
```bash
./gradlew test
```


