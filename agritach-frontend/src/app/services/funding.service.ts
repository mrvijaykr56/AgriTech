import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FundingMilestone } from '../models/funding-milestone.module';

@Injectable({ providedIn: 'root' })
export class MilestoneService {
  private baseUrl = 'http://localhost:8080/api/milestones';

  constructor(private http: HttpClient) {}

  getAll(): Observable<FundingMilestone[]> {
    return this.http.get<FundingMilestone[]>(this.baseUrl);
  }

  getById(id: number): Observable<FundingMilestone> {
    return this.http.get<FundingMilestone>(`${this.baseUrl}/${id}`);
  }

  getByCode(code: string): Observable<FundingMilestone> {
    return this.http.get<FundingMilestone>(`${this.baseUrl}/code/${code}`);
  }

  create(milestone: FundingMilestone): Observable<FundingMilestone> {
    return this.http.post<FundingMilestone>(this.baseUrl, milestone);
  }

  update(id: number, milestone: FundingMilestone): Observable<FundingMilestone> {
    return this.http.put<FundingMilestone>(`${this.baseUrl}/${id}`, milestone);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}