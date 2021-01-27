import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CulturalOffer } from '../model/cultural-offer';
import { Grade } from '../model/grade';
import { AuthService } from '../services/auth.service';
import { GradesService } from '../services/grades.service';

@Component({
  selector: 'app-grading',
  templateUrl: './grading.component.html',
  styleUrls: ['./grading.component.sass']
})
export class GradingComponent implements OnInit {

  public userGrade : number;
  public averageGrade : number;
  public grade: Grade;
  @Input() public culturalOffer: CulturalOffer;
  @Output() graded : EventEmitter<boolean> = new EventEmitter();
  public role: string;
  public logged : string;
  public targetRole : string = 'ROLE_GUEST';

  constructor(private gradingService: GradesService, private auth_service: AuthService) { 
    this.grade = {value:1, gradedOn: new Date(), userId:this.auth_service.getUserId(), 
      culturalOfferId: 0};

    this.role = this.auth_service.getRole();
    this.logged = this.auth_service.getToken();
  }

  ngOnInit(): void {
    if(this.logged && this.role == this.targetRole)
    {
      this.gradingService.getSpecificGrade(this.culturalOffer.id, this.auth_service.getUserId()).subscribe((res) => {
        setTimeout(() => {
          this.userGrade = res as number;
        }, 20);
        
      });
    }
  }

  aux(){
    setTimeout(() => this.gradeOffer(), 500);
  }

  gradeOffer(){
    //console.log(this.grade.culturalOfferId);
    this.grade.gradedOn = new Date();
    this.grade.value = this.userGrade;
    this.grade.culturalOfferId = this.culturalOffer.id;
    
    this.gradingService.addGrade(this.grade).subscribe((res) => {
      //console.log(res.value);
      this.graded.emit(true);
    });
  }
}
