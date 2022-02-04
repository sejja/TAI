import { Component, OnInit, HostListener} from '@angular/core';

@Component({
  selector: 'app-test-tai',
  templateUrl: './test-tai.component.html',
  styleUrls: ['./test-tai.component.css']
})
export class TestTAIComponent implements OnInit {


  ifase:any;
  nfase:any;
  myVar:boolean;
  

  constructor() { }

  ngOnInit() {
    this.ifase = 0;
    this.nfase = 9;
    this.myVar = true;
  }


  @HostListener('document:keypress', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) { 

    console.log("Evento --> " + this.ifase);
    
    if (event.key == " " && event.target == document.body) {
      event.preventDefault();
      if (this.ifase == 5){//reonocimento de imagenes

        this.ifase = (this.ifase + 1) % this.nfase;
      } else if (this.ifase == 6) {//reonocimento de palabras

        this.ifase = (this.ifase + 1) % this.nfase;
      } else if (this.ifase == 7) {//test 1

        this.ifase = (this.ifase + 1) % this.nfase;
      } else if (this.ifase == 8) {//test 2

        this.ifase = (this.ifase + 1) % this.nfase;
      } else{
        
        this.ifase = (this.ifase + 1) % this.nfase;
      }
    }

  }

}
