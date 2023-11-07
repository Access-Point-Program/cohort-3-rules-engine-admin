import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { WhenComponent } from './when.component';

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

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('dropdown should contain the correct enums', () => {
    it('First dropdown item should be \"FRONT\"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.whenDropdownOptions').item(0).textContent).toContain("FRONT");
    });

    it('Second dropdown item should be \"RIGHT\"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.whenDropdownOptions').item(1).textContent).toContain("RIGHT");
    });

    it('Third dropdown item should be \"LEFT\"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.whenDropdownOptions').item(2).textContent).toContain("LEFT");
    });

    it('Fourth dropdown item should be \"BEHIND\"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.whenDropdownOptions').item(3).textContent).toContain("BEHIND");
    });

    it('Dropdown only contains 4 options', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.whenDropdownOptions').length).toEqual(4);
    });
  });

  describe('when dropdown option is selected, it should pass the correct fact', () => {
    it('\"FRONT\" is clicked, returnedValue is \"FRONT\"', () => {
      (document.getElementById('whenSelectBox') as HTMLElement).click();
      (document.getElementById('whenFactFRONT') as HTMLElement).click();
      waitForAsync( async () => {expect(component.returnedValue).toBe('FRONT')});
    });
    it('\"RIGHT\" is clicked, returnedValue is \"RIGHT\"', () => {
      (document.getElementById('whenSelectBox') as HTMLElement).click();
      (document.getElementById('whenFactRIGHT') as HTMLElement).click();
      waitForAsync( async () => {expect(component.returnedValue).toBe('RIGHT')});
    });
    it('\"LEFT\" is clicked, returnedValue is \"LEFT\"', () => {
      (document.getElementById('whenSelectBox') as HTMLElement).click();
      (document.getElementById('whenFactLEFT') as HTMLElement).click();
      waitForAsync( async () => {expect(component.returnedValue).toBe('LEFT')});
    });
    it('\"BEHIND\" is clicked, returnedValue is \"BEHIND\"', () => {
      (document.getElementById('whenSelectBox') as HTMLElement).click();
      (document.getElementById('whenFactBEHIND') as HTMLElement).click();
      waitForAsync( async () => {expect(component.returnedValue).toBe('BEHIND')});
    });
  });
  
  it('\"When\" should be rendered on the screen', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.when')?.textContent).toContain('When');
  })
});
