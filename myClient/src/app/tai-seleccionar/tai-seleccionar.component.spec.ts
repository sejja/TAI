import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaiSeleccionarComponent } from './tai-seleccionar.component';

describe('TaiSeleccionarComponent', () => {
  let component: TaiSeleccionarComponent;
  let fixture: ComponentFixture<TaiSeleccionarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TaiSeleccionarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TaiSeleccionarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
