import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { WhenComponent } from './when.component';
import { By } from '@angular/platform-browser';

describe('WhenComponent', () => {
  let component: WhenComponent;
  let fixture: ComponentFixture<WhenComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WhenComponent]
    });
    fixture = TestBed.createComponent(WhenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create "when" component', () => {
    expect(component).toBeTruthy();
  });

  describe('dropdown should contain the correct enums', () => {
    it('First dropdown item should be \"Front\"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.whenDropdownOptions').item(0).textContent).toContain("Front");
    });

    it('Second dropdown item should be \"Right\"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.whenDropdownOptions').item(1).textContent).toContain("Right");
    });

    it('Third dropdown item should be \"Left\"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.whenDropdownOptions').item(2).textContent).toContain("Left");
    });

    it('Fourth dropdown item should be \"Behind\"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.whenDropdownOptions').item(3).textContent).toContain("Behind");
    });

    it('Dropdown only contains 4 options', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.whenDropdownOptions').length).toEqual(4);
    });
  });

  describe('when dropdown option is selected, it should pass the correct fact', () => {
    it('\"Front\" is clicked, returnedValue is \"Front\"', () => {
      let selectBox = fixture.debugElement.query(By.css('#whenSelectBox')).nativeElement;
      fixture.detectChanges();

      selectBox.value = selectBox.options[1].value;
      selectBox.dispatchEvent(new Event('change'))
 
      expect(component.whenValue).toBe('Front');
    });
    it('\"Right\" is clicked, returnedValue is \"Right\"', () => {
      let selectBox = fixture.debugElement.query(By.css('#whenSelectBox')).nativeElement;
      fixture.detectChanges();

      selectBox.value = selectBox.options[2].value;
      selectBox.dispatchEvent(new Event('change'))
 
      expect(component.whenValue).toBe('Right');
    });
    it('\"Left\" is clicked, returnedValue is \"Left\"', () => {
      let selectBox = fixture.debugElement.query(By.css('#whenSelectBox')).nativeElement;
      fixture.detectChanges();

      selectBox.value = selectBox.options[3].value;
      selectBox.dispatchEvent(new Event('change'))
 
      expect(component.whenValue).toBe('Left');
    });
    it('\"Behind\" is clicked, returnedValue is \"Behind\"', () => {
      let selectBox = fixture.debugElement.query(By.css('#whenSelectBox')).nativeElement;
      fixture.detectChanges();

      selectBox.value = selectBox.options[4].value;
      selectBox.dispatchEvent(new Event('change'))
 
      expect(component.whenValue).toBe('Behind');
    });
  });
  
  it('\"When\" should be rendered on the screen', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.when')?.textContent).toContain('When');
  })
});
