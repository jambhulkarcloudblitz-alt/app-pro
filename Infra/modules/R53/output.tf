# Hosted Zone ID

output "hosted_zone_id" {
  description = "Route 53 hosted zone ID"
  value       = aws_route53_zone.main.zone_id
}


# Name Servers

output "name_servers" {
  description = "Route 53 name servers"
  value       = aws_route53_zone.main.name_servers
}


# Hosted Zone ARN

output "hosted_zone_arn" {
  description = "Route 53 hosted zone ARN"
  value       = aws_route53_zone.main.arn
}


# Domain Name

output "domain_name" {
  description = "Domain name"
  value       = var.domain_name
}


# ALB Record Name

output "alb_record_name" {
  description = "ALB record name"
  value       = var.create_alb_record ? aws_route53_record.alb[0].name : null
}