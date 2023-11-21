import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';

import { CreateNewConditionComponent } from './create-new-condition.component';
import { RulesComponentComponent } from '../rules-component/rules-component.component';

describe('CreateNewConditionComponent', () => {
  let component: CreateNewConditionComponent;
  let fixture: ComponentFixture<CreateNewConditionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateNewConditionComponent, RulesComponentComponent],
      providers: [RulesComponentComponent]
    });
    fixture = TestBed.createComponent(CreateNewConditionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create "Add Condition" button', () => {
    expect(component).toBeTruthy();
  });

  it('\"Add Button\" should be rendered on the screen', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.addConditionButton')?.textContent).toContain('Add Condition');
  });

  it('When clicked, new conditions component is added to array of conditions', () => {
    let addConditionButton = fixture.debugElement.query(By.css('.addConditionButton')).nativeElement;
    addConditionButton.click();
    expect(component._parent.childrenConditions.length).toBe(2);
  });

});
