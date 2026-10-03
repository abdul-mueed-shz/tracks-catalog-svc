output "resource_group_name" {
  description = "Name of the Azure Resource Group"
  value       = module.resource_group.resource_group_name
}

output "app_url" {
  description = "Base URL of the Catalog Service on Azure Container Apps"
  value       = module.container_app.base_url
}

output "swagger_ui_url" {
  description = "Interactive Swagger UI URL"
  value       = module.container_app.swagger_ui_url
}

output "openapi_docs_url" {
  description = "OpenAPI 3 JSON specification URL"
  value       = module.container_app.openapi_docs_url
}

output "health_url" {
  description = "Spring Boot Actuator health probe URL"
  value       = module.container_app.health_url
}

output "postgres_fqdn" {
  description = "PostgreSQL Flexible Server host FQDN"
  value       = module.postgresql.server_fqdn
}

output "postgres_jdbc_url" {
  description = "JDBC connection string for PostgreSQL"
  value       = module.postgresql.jdbc_url
}

output "redis_hostname" {
  description = "Redis hostname (sidecar container or managed Redis)"
  value       = var.use_managed_redis ? module.redis[0].hostname : "localhost (container sidecar)"
}

output "acr_login_server" {
  description = "Login server for Azure Container Registry (if enabled)"
  value       = var.enable_acr ? module.acr[0].login_server : null
}
