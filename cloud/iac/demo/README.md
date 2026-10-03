# Azure Infrastructure as Code (Demo Environment)

This directory contains the **Terragrunt** deployment configuration for the **Music Catalog Service** on **Microsoft Azure**.

It provisions a cloud-native, cost-optimized demo stack that connects `catalog-service` to a managed PostgreSQL Flexible Server and Azure Cache for Redis, hosted on Azure Container Apps.

---

## Architecture Overview

```
                                [ Public Internet / Reviewers ]
                                               │
                                               ▼ (HTTPS :443)
                         ┌───────────────────────────────────────────┐
                         │      Azure Container Apps Ingress         │
                         │    https://<app-name>.<region>.azure...   │
                         └─────────────────────┬─────────────────────┘
                                               │ :8080
                                               ▼
                         ┌───────────────────────────────────────────┐
                         │   Azure Container App: catalog-service    │
                         │  - Context Path: /catalog-svc             │
                         │  - Actuator Probes: /actuator/health      │
                         │  - Interactive Swagger: /swagger-ui.html  │
                         └──────────────┬─────────────┬──────────────┘
                                        │             │
                JDBC (SSL mode=require) │             │ TLS (:6380)
                                        ▼             ▼
       ┌──────────────────────────────────┐         ┌──────────────────────────────┐
       │ Azure Database for PostgreSQL    │         │ Azure Cache for Redis        │
       │ - Flexible Server (v16)          │         │ - Basic Tier C0 (250 MB)     │
       │ - Burstable B_Standard_B1ms      │         │ - 24h Daily Artist Caching   │
       │ - DB: catalog                    │         └──────────────────────────────┘
       └──────────────────────────────────┘
```

---

## Directory Structure

```
cloud/iac/
├── main/                                # Reusable, provider-agnostic Terraform code
│   ├── modules/
│   │   ├── resource_group/              # Resource Group & Log Analytics Workspace
│   │   ├── postgresql/                  # Azure Database for PostgreSQL Flexible Server
│   │   ├── redis/                       # Azure Cache for Redis (Basic C0)
│   │   ├── container_app/               # Azure Container Apps Environment & Container App
│   │   └── acr/                         # Azure Container Registry (optional)
│   ├── main.tf                          # Orchestration composition connecting the modules
│   ├── variables.tf                     # Configurable variables with sensible defaults
│   ├── outputs.tf                       # Exposed URLs, hostnames, and credentials
│   └── versions.tf                      # Terraform & AzureRM provider constraints
│
└── demo/                                # Environment-specific Terragrunt configuration
    ├── terragrunt.hcl                   # Injects inputs, generates provider & state
    └── README.md                        # Deployment & operational guide
```

---

## Sizing & Azure Credit Optimization

This demo is tailored to **minimize Azure credit burn** while ensuring a responsive, real-world deployment:

| Component | Azure Resource | Sizing / SKU | Credit Impact |
|:---|:---|:---|:---|
| **Compute** | Azure Container Apps | 0.5 vCPU, 1.0 GiB RAM | **Free tier** (First 180k vCPU-s & 2M requests/mo free) |
| **Telemetry** | Log Analytics Workspace | `PerGB2018` | **Free tier** (First 5 GB/month ingestion free) |
| **Database** | Azure Database for PostgreSQL | `B_Standard_B1ms` (Burstable, 32GB) | ~$15 - $20 / month (pro-rated hourly) |
| **Cache** | Azure Cache for Redis | `Basic C0` (250 MB capacity) | ~$16 / month (pro-rated hourly) |
| **Total Cost** | Full Managed Stack | All managed PaaS | **~$1 / day** while running; **$0** once destroyed |

---

## Prerequisites

1. **Azure CLI (`az`)**: Installed and authenticated.
   ```bash
   az login
   az account list --output table
   az account set --subscription "<YOUR_SUBSCRIPTION_ID_OR_NAME>"
   ```
2. **Terraform** (`>= 1.5.0`) & **Terragrunt** (`>= 0.60` or `1.0.0+`):
   ```bash
   terraform --version
   terragrunt --version
   ```
3. *(Optional)* **Docker**: If you plan to build and push your own container image.

---

## Step-by-Step Deployment

### 1. Configure Demo Inputs

Edit [`terragrunt.hcl`](./terragrunt.hcl) to match your preferred region or container image:

```hcl
inputs = {
  project_name = "catalog-svc"
  environment  = "demo"
  location     = "switzerlandnorth"  # Allowed by subscription policy (lowest latency to Germany)

  # Container image to deploy
  container_image = "ghcr.io/abdul-mueed-shz/tracks-catalog-svc:latest"

  # Sizing (configured for minimum credit cost)
  postgres_sku = "B_Standard_B1ms"
  use_managed_redis = false # Zero-cost Redis 7 container co-located inside the pod
}
```

> **Note on Container Images**: By default, it points to `ghcr.io/abdul-mueed-shz/tracks-catalog-svc:latest`, which is automatically built and pushed to GitHub Container Registry on every git push via [`.github/workflows/deploy-image.yml`](../../../.github/workflows/deploy-image.yml). If you want to use a private Azure Container Registry instead, set `enable_acr = true`.

### 2. Plan the Deployment

Navigate to the `cloud/iac/demo` directory:

```bash
cd cloud/iac/demo
terragrunt plan
```

Terragrunt will:
1. Download the reusable Terraform modules from `../main`.
2. Generate the Azure provider and local state configuration.
3. Show you the plan containing all Azure resources to be provisioned.

### 3. Apply and Provision Infrastructure

```bash
terragrunt apply -auto-approve
```

Provisioning typically takes 5 to 8 minutes (PostgreSQL Flexible Server takes the majority of this time).

---

## Outputs & Verification

Upon completion, Terragrunt will output the public endpoints:

```text
Outputs:

app_url           = "https://ca-catalog-svc-demo.<region>.azurecontainerapps.io/catalog-svc"
swagger_ui_url    = "https://ca-catalog-svc-demo.<region>.azurecontainerapps.io/catalog-svc/swagger-ui.html"
openapi_docs_url  = "https://ca-catalog-svc-demo.<region>.azurecontainerapps.io/catalog-svc/v3/api-docs"
health_url        = "https://ca-catalog-svc-demo.<region>.azurecontainerapps.io/catalog-svc/actuator/health"
postgres_fqdn     = "psql-catalog-svc-demo-xxxx.postgres.database.azure.com"
redis_hostname    = "redis-catalog-svc-demo-xxxx.redis.cache.windows.net"
resource_group_name = "rg-catalog-svc-demo"
```

### 1. Test Swagger UI
Open the `swagger_ui_url` in your browser:
```
https://<your-container-app-fqdn>/catalog-svc/swagger-ui.html
```

### 2. Check Service Health
```bash
curl -i https://<your-container-app-fqdn>/catalog-svc/actuator/health
```
Expected response:
```json
{"status":"UP"}
```

### 3. Register an Artist via Live API
```bash
curl -X POST "https://<your-container-app-fqdn>/catalog-svc/users" \
  -H "Content-Type: application/json" \
  -d '{"name": "Adele", "isArtist": true}'
```

### 4. Connect Companion Demo UI
If you are running the frontend Demo UI ([tracks-catalog-svc-demo-ui](https://github.com/abdul-mueed-shz/tracks-catalog-svc-demo-ui)):
- Point the UI's `VITE_API_BASE_URL` to `https://<your-container-app-fqdn>/catalog-svc`.
- CORS is already pre-configured to allow requests from any origin.

---

## Destroying the Demo (Save Credits)

When you are done evaluating the demo, destroy the entire Azure stack with a single command to stop incurring charges:

```bash
cd cloud/iac/demo
terragrunt destroy -auto-approve
```

All resources (Container App, PostgreSQL, Redis, Resource Group) will be completely removed.
