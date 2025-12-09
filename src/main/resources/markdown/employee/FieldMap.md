---
resource: "employees://field-map"
name: "Employee Field Map"
description: >
  Defines all valid employee fields and their semantic meaning, including how
  each field should be used in queries. AI should use these field names exactly
  as defined when constructing QueryDSL requests.

schema:
  type: object
  properties:

    employee_id:
      type: number
      description: >
        Unique numeric identifier of the employee.  
        Suitable for equality checks or range filtering.

    first_name:
      type: string
      description: >
        Given name of the employee.  
        Typically used with match or term queries (text search).

    last_name:
      type: string
      description: >
        Family name / surname of the employee.  
        Supports exact matching or text searching.

    salary:
      type: number
      description: >
        Employee's numeric salary value.  
        Commonly used with range queries (gte, lte, gt, lt).

    email:
      type: string
      description: >
        Employee's corporate email address.  
        Usually queried using term (exact match) unless partial matching is needed.

    hire_date:
      type: string
      format: date
      description: >
        The date (YYYY-MM-DD) when the employee was hired.  
        Used in date range filters such as gte/lte.

    manager_first_name:
      type: string
      description: >
        First name of the employee's direct manager.  
        Supports match or exact term queries.

    manager_last_name:
      type: string
      description: >
        Last name of the employee's direct manager.  
        Supports match or exact term queries.

    deparment_name:
      type: string
      description: >
        Name of the department the employee belongs to (e.g., "Engineering").  
        Commonly used with match or term.

    project_name:
      type: string
      description: >
        Name of a project that the employee is assigned to.  
        Used in match or term queries.

    project_role:
      type: string
      description: >
        Role of the employee within a project (e.g., "Developer", "Lead").  
        Used in match/term filters.

---
