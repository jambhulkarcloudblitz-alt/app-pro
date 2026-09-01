# Route 53 Hosted Zone

resource "aws_route53_zone" "main" {
  name = var.domain_name

  tags = var.tags
}


# ALB A Record

resource "aws_route53_record" "alb" {
  count = var.create_alb_record ? 1 : 0

  zone_id = aws_route53_zone.main.zone_id
  name    = var.alb_subdomain
  type    = "A"

  alias {
    name                   = var.alb_dns_name
    zone_id                = var.alb_zone_id
    evaluate_target_health = true
  }
}