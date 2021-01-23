import { Component, Input, OnInit } from '@angular/core';
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
  @Input() public offerId: number;

  constructor(private gradingService: GradesService, private auth_service: AuthService) { 
    this.grade = {value:1, gradedOn: new Date(), userId:this.auth_service.getUserId(), culturalOfferId: 0};
  }

  ngOnInit(): void {

    this.gradingService.getSpecificGrade("14", this.auth_service.getUserId()).subscribe((res) => {
      this.userGrade = res as number;
    });

    this.gradingService.getAvgGrade("14").subscribe((res) => {
      this.averageGrade = res;
    });
  }

  aux(){
    setTimeout(() => this.gradeOffer(), 500);
  }

  gradeOffer(){
    this.grade.gradedOn = new Date();
    this.grade.culturalOfferId = 14;
    this.grade.userId = 1001;
    this.grade.value = this.userGrade;
    console.log(this.grade.value);
    this.gradingService.addGrade(this.grade).subscribe((res) => {
      console.log(res.value);
      this.gradingService.getAvgGrade("14").subscribe((res) => {
        this.averageGrade = res;
      });
    });
  }
}
