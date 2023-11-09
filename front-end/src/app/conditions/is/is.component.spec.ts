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
      (document.getElementById('isSelectBox') as HTMLElement).click();
      (document.getElementById('isValueWall') as HTMLElement).click();
      waitForAsync( async () => {expect(component.returnedValue).toBe('Wall')});
    });
    it('\"Empty\" is clicked, returnedValue is \"Empty\"', () => {
      (document.getElementById('isSelectBox') as HTMLElement).click();
      (document.getElementById('isValueEmpty') as HTMLElement).click();
      waitForAsync( async () => {expect(component.returnedValue).toBe('Empty')});
    });
    it('\"End\" is clicked, returnedValue is \"End\"', () => {
      (document.getElementById('isSelectBox') as HTMLElement).click();
      (document.getElementById('isValueEnd') as HTMLElement).click();
      waitForAsync( async () => {expect(component.returnedValue).toBe('End')});
    });
  });
  
  it('\"is\" should be rendered on the screen', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.is')?.textContent).toContain('is');
  });
});