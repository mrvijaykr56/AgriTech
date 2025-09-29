import { Routes } from '@angular/router';
import { FarmerComponent } from './component/farmer/farmer.component';
import { InspectionComponent } from './component/inspection/inspection.component';
import { ConsentComponent } from './component/consent/consent.component';
import { MilestoneComponent } from './component/funding-milestone/funding-milestone.component';


export const routes: Routes = [
  { path: 'farmers', component: FarmerComponent },
  { path: 'inspections', component: InspectionComponent },
  { path: 'funding', component: MilestoneComponent },
  { path: 'consent', component: ConsentComponent },
  { path: '', redirectTo: '/farmers', pathMatch: 'full' },
  { path: '**', redirectTo: '/farmers' }
];