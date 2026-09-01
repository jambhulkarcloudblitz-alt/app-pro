output "hosted_zone_id" {
  description = "Route 53 hosted zone ID"
  value       = module.route53.hosted_zone_id
}


output "name_servers" {
  description = "Route 53 name servers"
  value       = module.route53.name_servers
}


output "hosted_zone_arn" {
  description = "Route 53 hosted zone ARN"
  value       = module.route53.hosted_zone_arn
}


output "domain_name" {
  description = "Domain name"
  value       = module.route53.domain_name
}