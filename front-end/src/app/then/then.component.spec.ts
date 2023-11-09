import { ComponentFixture, TestBed, fakeAsync, tick, waitForAsync } from '@angular/core/testing';

import { ThenComponent } from './then.component';
import { By } from '@angular/platform-browser';

describe('ThenComponent', () => {
  let component: ThenComponent;
  let fixture: ComponentFixture<ThenComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ThenComponent]
    });
    fixture = TestBed.createComponent(ThenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  describe('dropdown should contain the correct enums', () => {
    it('First dropdown item should be \"Forward\"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.thenDropdownOptions').item(0).textContent).toContain("Forward");
    });

    it('Second dropdown item should be \"Right\"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.thenDropdownOptions').item(1).textContent).toContain("Right");
    });

    it('Third dropdown item should be \"Left\"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.thenDropdownOptions').item(2).textContent).toContain("Left");
    });

    it('Dropdown only contains 3 options', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.thenDropdownOptions').length).toEqual(3);
    });
  });

  describe('when dropdown option is selected, it should pass the correct fact', () => {
    it('\"Forward\" is clicked, returnedValue is \"Forward\"', () => {
      let selectBox = fixture.debugElement.query(By.css('#thenSelectBox')).nativeElement;
      fixture.detectChanges();

      selectBox.value = selectBox.options[1].value;
      selectBox.dispatchEvent(new Event('change'))
 
      expect(component.getReturnedValue()).toBe('Forward');
    });
    it('\"Right\" is clicked, returnedValue is \"Right\"', () => {
      let selectBox = fixture.debugElement.query(By.css('#thenSelectBox')).nativeElement;
      fixture.detectChanges();

      selectBox.value = selectBox.options[2].value;
      selectBox.dispatchEvent(new Event('change'))
 
      expect(component.getReturnedValue()).toBe('Right');
    });
    it('\"Left\" is clicked, returnedValue is \"Left\"', fakeAsync( () => {
      let selectBox = fixture.debugElement.query(By.css('#thenSelectBox')).nativeElement;
      fixture.detectChanges();

      selectBox.value = selectBox.options[3].value;
      selectBox.dispatchEvent(new Event('change'))
 
      expect(component.getReturnedValue()).toBe('Left');
    }));
  });
  
  it('\"Then\" should be rendered on the screen', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.then')?.textContent).toContain('Then');
  });
});
