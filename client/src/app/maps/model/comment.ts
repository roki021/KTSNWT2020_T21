export interface CommentInt {
    id?:any;
    content:string;
    commentedOn:Date;
    imageUrls: string[];
    userId:number;
    userUsername?:string;
    culturalOfferId:number;
    culturalOfferName?:string;
}