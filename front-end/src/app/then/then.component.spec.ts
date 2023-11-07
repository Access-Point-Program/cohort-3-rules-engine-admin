import { ComponentFixture, TestBed } from '@angular/core/testing';

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

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
