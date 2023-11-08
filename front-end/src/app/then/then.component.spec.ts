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
    it('\"FORWARD\" is clicked, returnedValue is \"Forward\"', () => {
      (document.getElementById('thenSelectBox') as HTMLElement).click();
      (document.getElementById('thenEventForward') as HTMLElement).click();
      waitForAsync( async () => {expect(component.returnedValue).toBe('Forward')});
    });
    it('\"RIGHT\" is clicked, returnedValue is \"Right\"', () => {
      (document.getElementById('thenSelectBox') as HTMLElement).click();
      (document.getElementById('thenEventRight') as HTMLElement).click();
      waitForAsync( async () => {expect(component.returnedValue).toBe('Right')});
    });
    it('\"LEFT\" is clicked, returnedValue is \"Left\"', () => {
      (document.getElementById('thenSelectBox') as HTMLElement).click();
      (document.getElementById('thenEventLeft') as HTMLElement).click();
      waitForAsync( async () => {expect(component.returnedValue).toBe('Left')});
    });
  });
  
  it('\"Then\" should be rendered on the screen', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.then')?.textContent).toContain('Then');
  });
});
