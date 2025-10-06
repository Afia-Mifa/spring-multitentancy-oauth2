# 🏗️ Multi-Tenant Architecture with OAuth2 — Spring Boot 3.5.0

This project is a **work in progress** implementation of a **multi-tenant architecture** secured with **OAuth2**, built using **Spring Boot 3.5.0**.  
The goal is to provide a **scalable and secure foundation** for SaaS or enterprise applications that serve multiple tenants under a single platform.

---

## 🚧 Project Status
This project is currently **under development**.  
Core modules such as **tenant context handling**, **dynamic data source resolution**, and **OAuth2-based authentication** are being implemented.

---

## 🧩 Architecture Overview

### 🔹 Multi-Tenancy
- Supports **tenant isolation** through database-level separation (schema or database-based).
- Implements **tenant resolution** via request headers, subdomains, or authentication tokens.
- Each tenant can maintain independent configurations and data.

### 🔹 OAuth2 Security
- Secured using **Spring Security OAuth2** with clean, extensible configuration.
- Designed for integration with both **Auth Server** and **Resource Server** setups.
- Provides a foundation for **role-based access control (RBAC)** per tenant.

---

## ⚙️ Tech Stack
- **Java 21+**
- **Spring Boot 3.5.0**
- **Spring Security (OAuth2)**
- **Spring Data JPA**
- **Gradle**
- **MySQL**
- **Flyway**

---

## 🚀 Planned Features
- ✅ Basic OAuth2 authentication setup
- ✅ Tenant context propagation
- ✅ Dynamic DataSource configuration
- ⏳ Tenant-based user and role management
- ⏳ Centralized logging and monitoring
- ⏳ REST API for tenant registration and management

---