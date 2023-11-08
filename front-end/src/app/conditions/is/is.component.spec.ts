import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { IsComponent } from './is.component';

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

  describe('dropdown should contain the correct enums', () => {
    it('First dropdown item should be \"WALL\"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.isDropdownOptions').item(0).textContent).toContain("WALL");
    });

    it('Second dropdown item should be \"EMPTY\"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.isDropdownOptions').item(1).textContent).toContain("EMPTY");
    });

    it('Third dropdown item should be \"END\"', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.isDropdownOptions').item(2).textContent).toContain("END");
    });

    it('Dropdown only contains 3 options', () => {
      const compiled = fixture.nativeElement as HTMLElement;
      expect(compiled.querySelectorAll('.isDropdownOptions').length).toEqual(3);
    });
  });

  describe('when dropdown option is selected, it should pass the correct fact', () => {
    it('\"WALL\" is clicked, returnedValue is \"WALL\"', () => {
      (document.getElementById('isSelectBox') as HTMLElement).click();
      (document.getElementById('isValueWALL') as HTMLElement).click();
      waitForAsync( async () => {expect(component.returnedValue).toBe('WALL')});
    });
    it('\"EMPTY\" is clicked, returnedValue is \"EMPTY\"', () => {
      (document.getElementById('isSelectBox') as HTMLElement).click();
      (document.getElementById('isValueEMPTY') as HTMLElement).click();
      waitForAsync( async () => {expect(component.returnedValue).toBe('EMPTY')});
    });
    it('\"END\" is clicked, returnedValue is \"END\"', () => {
      (document.getElementById('isSelectBox') as HTMLElement).click();
      (document.getElementById('isValueEND') as HTMLElement).click();
      waitForAsync( async () => {expect(component.returnedValue).toBe('END')});
    });
  });
  
  it('\"is\" should be rendered on the screen', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.is')?.textContent).toContain('is');
  });
});