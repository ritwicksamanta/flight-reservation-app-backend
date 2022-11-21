import { AbstractControl, FormGroup, ValidationErrors, ValidatorFn } from "@angular/forms";

export function passwordValidator():ValidatorFn{
  return (control:AbstractControl):ValidationErrors|null => {
    const formGroup = control as FormGroup;

    const password = formGroup.get("password")?.value;
    const confirmPassword = formGroup.get("confirmPassword")?.value;

    return password === confirmPassword? null:{ noMatch  : true};
  }
}
