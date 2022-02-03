import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-test-atencion',
  templateUrl: './test-atencion.component.html',
  styleUrls: ['./test-atencion.component.css']
})


export class TestAtencionComponent implements OnInit {

  //constructor() { }
  constructor(private ruta: ActivatedRoute, private router: Router) {}

  ngOnInit() {

  }

}
