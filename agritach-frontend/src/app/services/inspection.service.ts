import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Inspection } from '../models/inspection.module';


@Injectable({ providedIn: 'root' })
export class InspectionService {
  private baseUrl = 'http://localhost:8080/api/inspections';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Inspection[]> {
    return this.http.get<Inspection[]>(this.baseUrl);
  }

  getById(id: string): Observable<Inspection> {
    return this.http.get<Inspection>(`${this.baseUrl}/${id}`);
  }

  getByField(fieldId: string): Observable<Inspection[]> {
    return this.http.get<Inspection[]>(`${this.baseUrl}/field/${fieldId}`);
  }

create(inspection: Partial<Inspection>): Observable<Inspection> {
  const payload = {
    commodity: inspection.commodity,
    label: inspection.label,
    confidence: inspection.confidence,
    riskLevel: inspection.riskLevel,
    latitude: inspection.latitude,
    longitude: inspection.longitude,
    field: { fieldId: inspection.fieldId }   // 👈 wrap fieldId
  };
  return this.http.post<Inspection>(this.baseUrl, payload);
}

delete(id: number): Observable<void> {
  return this.http.delete<void>(`${this.baseUrl}/${id}`);
}

}