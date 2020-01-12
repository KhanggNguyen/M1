import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RechercheResultatComponent } from './recherche-resultat.component';

describe('RechercheResultatComponent', () => {
  let component: RechercheResultatComponent;
  let fixture: ComponentFixture<RechercheResultatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RechercheResultatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RechercheResultatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
