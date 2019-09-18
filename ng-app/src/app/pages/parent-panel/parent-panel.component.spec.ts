import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ParentPanelComponent} from './parent-panel.component';

describe('ParentPanelComponent', () => {
  let component: ParentPanelComponent;
  let fixture: ComponentFixture<ParentPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ParentPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ParentPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
