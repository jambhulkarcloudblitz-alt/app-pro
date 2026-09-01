terraform {
  required_version = ">= 1.0"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}


provider "aws" {
  region = var.aws_region
}


module "s3" {
  source = "../../../modules/s3"

  domain_name       = var.domain_name
  enable_versioning = var.enable_versioning
  index_document    = var.index_document
  error_document    = var.error_document

  tags = var.tags
}