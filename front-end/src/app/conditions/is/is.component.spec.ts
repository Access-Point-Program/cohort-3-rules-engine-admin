import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { IsComponent } from './is.component';
import { By } from '@angular/platform-browser';

describe('IsComponent', () => {
  let component: IsComponent;
  let fixture: ComponentFixture<IsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IsComponent]
    });
    fixture = TestBed.createComponent(IsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create "is" component', () => {
    expect(component).toBeTruthy();
  });

  describe('dropdown should contain the correct enums', () => {
    it('First dropdown item should be \"Wall\"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.isDropdownOptions').item(0).textContent).toContain("Wall");
    });

    it('Second dropdown item should be \"Empty\"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.isDropdownOptions').item(1).textContent).toContain("Empty");
    });

    it('Third dropdown item should be \"End\"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.isDropdownOptions').item(2).textContent).toContain("End");
    });

    it('Dropdown only contains 3 options', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.isDropdownOptions').length).toEqual(3);
    });
  });

  describe('when dropdown option is selected, it should pass the correct fact', () => {
    it('\"Wall\" is clicked, returnedValue is \"Wall\"', () => {
      let selectBox = fixture.debugElement.query(By.css('#isSelectBox')).nativeElement;
      fixture.detectChanges();

      selectBox.value = selectBox.options[1].value;
      selectBox.dispatchEvent(new Event('change'))
 
      expect(component.getReturnedValue()).toBe('Wall');
    });
    it('\"Empty\" is clicked, returnedValue is \"Empty\"', () => {
      let selectBox = fixture.debugElement.query(By.css('#isSelectBox')).nativeElement;
      fixture.detectChanges();

      selectBox.value = selectBox.options[2].value;
      selectBox.dispatchEvent(new Event('change'))
 
      expect(component.getReturnedValue()).toBe('Empty');
    });
    it('\"End\" is clicked, returnedValue is \"End\"', () => {
      let selectBox = fixture.debugElement.query(By.css('#isSelectBox')).nativeElement;
      fixture.detectChanges();

      selectBox.value = selectBox.options[3].value;
      selectBox.dispatchEvent(new Event('change'))
 
      expect(component.getReturnedValue()).toBe('End');
    });
  });
  
  it('\"is\" should be rendered on the screen', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.is')?.textContent).toContain('is');
  });
});