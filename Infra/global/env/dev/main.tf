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


module "route53" {
  source = "../../../modules/route53"

  domain_name = var.domain_name

  tags = var.tags
}