import { TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { WhenComponent } from './conditions/when/when.component';
import { IsComponent } from './conditions/is/is.component';
import { ThenComponent } from './then/then.component';
import { ConditionsComponent } from './conditions/conditions.component';

describe('AppComponent', () => {
  beforeEach(() => TestBed.configureTestingModule({
    declarations: [AppComponent,
      WhenComponent,
      IsComponent,
      ThenComponent,
      ConditionsComponent
    ]
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'Rules Engine Admin'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('Rules Engine Admin');
  });

  it('should render title', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('h1')?.textContent).toContain('Hello, This is Cyclones!');
  });
});
