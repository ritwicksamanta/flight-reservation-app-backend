import { AbstractControl, FormGroup, ValidationErrors, ValidatorFn } from "@angular/forms";

export function dateValidator():ValidatorFn{
  return (control:AbstractControl):ValidationErrors|null =>{

    const formGroup = control as FormGroup;
    const start=new Date(formGroup.get('fromDate')?.value);
    const end= new Date(formGroup.get('toDate')?.value);
    return end>start?null:{constraintViolation:true}
  }
}
