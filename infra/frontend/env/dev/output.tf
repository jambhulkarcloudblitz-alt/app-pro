output "bucket_name" {
  description = "S3 bucket name"
  value       = module.s3.bucket_name
}


output "bucket_arn" {
  description = "S3 bucket ARN"
  value       = module.s3.bucket_arn
}


output "bucket_domain_name" {
  description = "S3 bucket domain name"
  value       = module.s3.bucket_domain_name
}


output "website_endpoint" {
  description = "S3 static website endpoint"
  value       = module.s3.website_endpoint
}