import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteRuleButtonComponent } from './delete-rule-button.component';
import { CreateRulesetComponent } from '../create-ruleset/create-ruleset.component';
import { By } from '@angular/platform-browser';

describe('DeleteRuleButtonComponent', () => {
  let component: DeleteRuleButtonComponent;
  let fixture: ComponentFixture<DeleteRuleButtonComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeleteRuleButtonComponent],
      providers: [CreateRulesetComponent]
    });
    fixture = TestBed.createComponent(DeleteRuleButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create "delete-rule" component', () => {
    expect(component).toBeTruthy();
  });

  it('should render "delete" button', () => {
    const saveButton = fixture.debugElement.query(By.css('#deleteButtonInside'));
    expect(saveButton).toBeTruthy();
  });

  it('should render "delete" text on screen', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('#deleteButtonInside')?.textContent).toContain("Delete");
  });

  it('when "delete" button is clicked, call the deleteRule method', () => {
    fixture.detectChanges();
    spyOn(component, 'deleteRule');
    fixture.debugElement.nativeElement.querySelector('#deleteButtonInside').click();
    fixture.detectChanges();
    expect(component.deleteRule).toHaveBeenCalled();
  })
});