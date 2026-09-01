# Domain Name

variable "domain_name" {
  description = "Domain name to use as the S3 bucket name"
  type        = string
}


# Versioning

variable "enable_versioning" {
  description = "Enable versioning on the S3 bucket"
  type        = bool
  default     = true
}


# Website Configuration

variable "index_document" {
  description = "Index document for the static website"
  type        = string
  default     = "index.html"
}

variable "error_document" {
  description = "Error document for the static website"
  type        = string
  default     = "error.html"
}


# Tags

variable "tags" {
  description = "Tags for the S3 bucket"
  type        = map(string)
  default     = {}
}