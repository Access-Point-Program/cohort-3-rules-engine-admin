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
    // beforeEach(() => {
    //   (document.getElementById('thenSelectBox') as HTMLElement).click();
    //   (document.getElementById('thenEventLeft') as HTMLElement).click();
    // });
    it('\"Forward\" is clicked, returnedValue is \"Forward\"', () => {
      // (document.getElementById('thenSelectBox') as HTMLElement).click();
      // (document.getElementById('thenEventForward') as HTMLElement).click();
      waitForAsync( async () => {expect(component.getReturnedValue()).toBe('Forward')});
    });
    it('\"Right\" is clicked, returnedValue is \"Right\"', () => {
      // (document.getElementById('thenSelectBox') as HTMLElement).click();
      // (document.getElementById('thenEventRight') as HTMLElement).click();
      waitForAsync( async () => {expect(component.getReturnedValue()).toBe('s')});
    });
    it('\"Left\" is clicked, returnedValue is \"Left\"', fakeAsync( () => {
      let selectBox = fixture.debugElement.query(By.css('#thenSelectBox'));
      (document.getElementById('thenSelectBox') as HTMLElement).click();
      selectBox.triggerEventHandler('click', (document.getElementById('thenSelectBox') as HTMLElement));
      tick();
      console.log(selectBox);

      let selectBoxOption = fixture.debugElement.query(By.css('#thenEventLeft'));
      selectBoxOption.triggerEventHandler('click', (document.getElementById('thenEventLeft') as HTMLElement));
      tick();
      
      console.log(selectBoxOption);

      expect(() => {
        return component.getReturnedValue() as string}).toBe('Left');
    }));
  });
  
  it('\"Then\" should be rendered on the screen', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.then')?.textContent).toContain('Then');
  });
});
