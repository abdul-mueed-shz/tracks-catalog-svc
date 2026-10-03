output "redis_id" {
  description = "ID of the Redis Cache"
  value       = azurerm_redis_cache.this.id
}

output "redis_name" {
  description = "Name of the Redis Cache instance"
  value       = azurerm_redis_cache.this.name
}

output "hostname" {
  description = "Hostname of the Redis Cache"
  value       = azurerm_redis_cache.this.hostname
}

output "ssl_port" {
  description = "SSL port of the Redis Cache"
  value       = azurerm_redis_cache.this.ssl_port
}

output "port" {
  description = "Non-SSL port of the Redis Cache"
  value       = azurerm_redis_cache.this.port
}

output "primary_access_key" {
  description = "Primary access key for the Redis Cache"
  value       = azurerm_redis_cache.this.primary_access_key
  sensitive   = true
}
