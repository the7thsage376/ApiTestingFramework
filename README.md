# API Testing Framework

A lightweight, end-to-end API testing framework for validating user registration and account management workflows. <br>
Built with RestAssured and TestNG, 
it executes a full lifecycle test scenario: admin login → user registration → account approval → role elevation → verification → cleanup.

---

## Features:

- **Full E2E Test Coverage** — exercises login, registration, user approval, role assignment, and deletion flows
- **Modular Design** — request builders and payload builders for clean, maintainable tests
- **Allure Reporting** — integrated reporting for CI/CD pipelines
- **Test Data Generation** — uses javafaker for realistic test data
- **Configuration Management** — externalized config with support for override via system properties
- **Parallel Execution** — configured for concurrent test methods via Maven Surefire

---

## Tech Stack:

| Component | Version |
|-----------|---------|
| Java | 21 |
| Maven | (latest) |
| RestAssured | 6.0.0 |
| TestNG | 7.11.0 |
| Allure TestNG | 2.29.1 |
| Gson | 2.13.0 |
| javafaker | 1.0.2 |

---

## Quick Start

### Prerequisites:

- JDK 21 or higher
- Maven 3.8+
- (Optional) Allure CLI 2.x for report visualization

### Configuration:

Edit `src/main/resources/config.properties` with your test credentials:
```properties
admin.email=your-admin@email.com
admin.password=your-password
group.Id=your-group-uuid
```

Or pass them as system properties when running tests.

---

### Run Tests

**From IntelliJ IDEA** (recommended):
1. Right-click `src/main/java/Basic/userRegistration.java`
2. Select **Run 'userRegistration'** or create a TestNG run configuration

---

### Run Tests

**From IntelliJ IDEA** (recommended):
1. Right-click `src/main/java/Basic/userRegistration.java`
2. Select **Run 'userRegistration'** or create a TestNG run configuration

---

**From Command Line**:

```powershell
# Using config.properties
mvn test

# Override config with system properties
mvn test -Dadmin.email=test@example.com -Dadmin.password=pass -Dgroup.Id=uuid
```

Tests execute in parallel (2 threads by default) and write results to `allure-results/`.

---

## Project Structure:

```
src/main/java/
├── Basic/
│   └── userRegistration.java          # E2E test suite
├── requestBuilder/
│   └── ApiRequestBuilder.java         # HTTP request wrappers
├── payloadBuilder/
│   └── payloadBuilder.java            # Request body constructors
└── common/
    ├── BaseUri.java                   # Base URL & config constants
    └── ConfigLoader.java              # Property file loader

src/main/resources/
└── config.properties                  # Test credentials & environment
```

## Generate Allure Report:

After running tests, generate a visual HTML report:

```powershell
allure serve allure-results
```

This starts a local web server showing test execution details, timelines, and failure analysis.

## Notes

- **Test Target**: Tests hit the API at `https://www.ndosiautomation.co.za` by default. Override in `common/BaseUri.java` for different environments.
- **Secret Management**: Never commit credentials to version control. Use CI/CD secrets or environment variables in production.
- **Concurrency**: Tests are configured for parallel execution (2 threads). Adjust `pom.xml` if needed.
- **Dependencies**: MySQL connector is included but optional; remove from `pom.xml` if unused.
