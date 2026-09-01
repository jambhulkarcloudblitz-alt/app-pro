# Domain Name

variable "domain_name" {
  description = "Domain name for the Route 53 hosted zone"
  type        = string
}


# ALB Record

variable "create_alb_record" {
  description = "Whether to create an ALB A record"
  type        = bool
  default     = false
}

variable "alb_subdomain" {
  description = "Subdomain for the ALB"
  type        = string
  default     = ""
}

variable "alb_dns_name" {
  description = "DNS name of the ALB"
  type        = string
  default     = ""
}

variable "alb_zone_id" {
  description = "Hosted zone ID of the ALB"
  type        = string
  default     = ""
}


# Tags

variable "tags" {
  description = "Tags for Route 53 resources"
  type        = map(string)
  default     = {}
}