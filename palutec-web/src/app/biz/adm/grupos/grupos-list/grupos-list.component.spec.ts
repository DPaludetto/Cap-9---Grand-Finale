import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GrupoResolucaoListComponent } from './grupos-list.component';

describe('GruposListComponent', () => {
  let component: GrupoResolucaoListComponent;
  let fixture: ComponentFixture<GrupoResolucaoListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GrupoResolucaoListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GrupoResolucaoListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
