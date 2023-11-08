import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ThenComponent } from './then.component';

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
    it('First dropdown item should be \"FORWARD\"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.thenDropdownOptions').item(0).textContent).toContain("FORWARD");
    });

    it('Second dropdown item should be \"RIGHT\"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.thenDropdownOptions').item(1).textContent).toContain("RIGHT");
    });

    it('Third dropdown item should be \"LEFT\"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.thenDropdownOptions').item(2).textContent).toContain("LEFT");
    });

    it('Dropdown only contains 3 options', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.thenDropdownOptions').length).toEqual(3);
    });
  });

  describe('when dropdown option is selected, it should pass the correct fact', () => {
    it('\"FORWARD\" is clicked, returnedValue is \"FORWARD\"', () => {
      (document.getElementById('thenSelectBox') as HTMLElement).click();
      (document.getElementById('thenEventFORWARD') as HTMLElement).click();
      waitForAsync( async () => {expect(component.returnedValue).toBe('FORWARD')});
    });
    it('\"RIGHT\" is clicked, returnedValue is \"RIGHT\"', () => {
      (document.getElementById('thenSelectBox') as HTMLElement).click();
      (document.getElementById('thenEventRIGHT') as HTMLElement).click();
      waitForAsync( async () => {expect(component.returnedValue).toBe('RIGHT')});
    });
    it('\"LEFT\" is clicked, returnedValue is \"LEFT\"', () => {
      (document.getElementById('thenSelectBox') as HTMLElement).click();
      (document.getElementById('thenEventLEFT') as HTMLElement).click();
      waitForAsync( async () => {expect(component.returnedValue).toBe('LEFT')});
    });
  });
  
  it('\"Then\" should be rendered on the screen', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.then')?.textContent).toContain('Then');
  });
});
