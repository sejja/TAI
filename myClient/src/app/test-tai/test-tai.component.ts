import { Component, OnInit, HostListener} from '@angular/core';

@Component({
  selector: 'app-test-tai',
  templateUrl: './test-tai.component.html',
  styleUrls: ['./test-tai.component.css']
})
export class TestTAIComponent implements OnInit {


  ifase:Number;
  nfase:Number;
  myVar:boolean;
  

  constructor() { }

  ngOnInit() {
    this.ifase = 0;
    this.nfase = 9;
    this.myVar = true;
  }


  @HostListener('document:keypress', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) { 
    console.log("Evento --> " + event.view);
    this.myVar = !this.myVar;
  }

}
