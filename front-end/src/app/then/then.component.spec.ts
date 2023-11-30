import { ComponentFixture, TestBed, fakeAsync } from '@angular/core/testing';
import { ThenComponent } from './then.component';
import { RulesComponentComponent } from '../rules-component/rules-component.component';
import { By } from '@angular/platform-browser';

describe('ThenComponent', () => {
  let component: ThenComponent;
  let fixture: ComponentFixture<ThenComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ThenComponent],
      providers: [RulesComponentComponent]
    });
    fixture = TestBed.createComponent(ThenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create "then" component', () => {
    expect(component).toBeTruthy();
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
      let selectBox = fixture.debugElement.query(By.css('#thenSelectBox')).nativeElement;
      fixture.detectChanges();

      selectBox.value = selectBox.options[1].value;
      selectBox.dispatchEvent(new Event('change'))
 
      expect(component.thenValue).toBe('FORWARD');
    });
    it('\"RIGHT\" is clicked, returnedValue is \"RIGHT\"', () => {
      let selectBox = fixture.debugElement.query(By.css('#thenSelectBox')).nativeElement;
      fixture.detectChanges();

      selectBox.value = selectBox.options[2].value;
      selectBox.dispatchEvent(new Event('change'))
 
      expect(component.thenValue).toBe('RIGHT');
    });
    it('\"LEFT\" is clicked, returnedValue is \"LEFT\"', fakeAsync( () => {
      let selectBox = fixture.debugElement.query(By.css('#thenSelectBox')).nativeElement;
      fixture.detectChanges();

      selectBox.value = selectBox.options[3].value;
      selectBox.dispatchEvent(new Event('change'))
 
      expect(component.thenValue).toBe('LEFT');
    }));
  });
  
  it('\"Then\" should be rendered on the screen', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.then')?.textContent).toContain('Then');
  });
});