# Terragrunt configuration for Catalog Service Demo deployment on Microsoft Azure

terraform {
  source = "../main"
}

# Generate Azure Provider configuration
generate "provider" {
  path      = "provider.tf"
  if_exists = "overwrite_terragrunt"
  contents  = <<EOF
provider "azurerm" {
  features {
    resource_group {
      prevent_deletion_if_contains_resources = false
    }
  }
}
EOF
}

# Local state backend for simple, self-contained demo deployment without external state bucket dependencies.
# (To use Azure Blob Storage remote backend, replace backend = "azurerm" and specify storage_account_name)
remote_state {
  backend = "local"
  config = {
    path = "${get_terragrunt_dir()}/terraform.tfstate"
  }
  generate = {
    path      = "backend.tf"
    if_exists = "overwrite_terragrunt"
  }
}

# Configurable inputs injected into the reusable Azure modules in ../main
inputs = {
  project_name = "catalog-svc"
  environment  = "demo"
  location     = "switzerlandnorth" # Allowed by subscription policy: switzerlandnorth, polandcentral, italynorth, spaincentral

  # PostgreSQL Flexible Server (Burstable tier to minimize Azure credit consumption)
  postgres_version    = "16"
  postgres_sku        = "B_Standard_B1ms" # 1 vCPU, 2GB RAM
  postgres_storage_mb = 32768             # 32 GB minimum
  postgres_database_name = "catalog"
  postgres_admin_login   = "catalogadmin"

  # Azure Cache for Redis (Basic C0 tier: 250MB, minimum cost for demo)
  redis_sku      = "Basic"
  redis_family   = "C"
  redis_capacity = 0

  # Azure Container Apps (Microservice hosting)
  # Default image or your custom built image (e.g. from GitHub Container Registry or Docker Hub)
  container_image = "ghcr.io/abdul-mueed-shz/tracks-catalog-svc:latest"
  container_cpu    = 0.5
  container_memory = "1.0Gi"
  min_replicas     = 1
  max_replicas     = 2

  # Set enable_acr = true if you prefer to push your image to a private Azure Container Registry
  enable_acr = false

  tags = {
    Project     = "catalog-service"
    Environment = "demo"
    ManagedBy   = "Terragrunt"
  }
}
