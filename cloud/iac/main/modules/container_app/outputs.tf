output "container_app_id" {
  description = "ID of the Container App"
  value       = azurerm_container_app.this.id
}

output "container_app_name" {
  description = "Name of the Container App"
  value       = azurerm_container_app.this.name
}

output "environment_id" {
  description = "ID of the Container App Environment"
  value       = azurerm_container_app_environment.this.id
}

output "fqdn" {
  description = "Fully qualified domain name of the Container App"
  value       = azurerm_container_app.this.ingress[0].fqdn
}

output "base_url" {
  description = "Base application URL including context path"
  value       = "https://${azurerm_container_app.this.ingress[0].fqdn}/catalog-svc"
}

output "swagger_ui_url" {
  description = "Direct URL to interactive Swagger UI"
  value       = "https://${azurerm_container_app.this.ingress[0].fqdn}/catalog-svc/swagger-ui.html"
}

output "openapi_docs_url" {
  description = "Direct URL to OpenAPI 3 JSON document"
  value       = "https://${azurerm_container_app.this.ingress[0].fqdn}/catalog-svc/v3/api-docs"
}

output "health_url" {
  description = "Direct URL to service health check"
  value       = "https://${azurerm_container_app.this.ingress[0].fqdn}/catalog-svc/actuator/health"
}
