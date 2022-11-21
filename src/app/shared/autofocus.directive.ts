import { AfterViewInit, Directive, ElementRef } from '@angular/core';

@Directive({
  selector: '[appAutofocus]'
})
export class AutofocusDirective implements AfterViewInit{

  constructor(private _elementRef:ElementRef) { }
  ngAfterViewInit() {
    this._elementRef.nativeElement.focus();
  }

}
