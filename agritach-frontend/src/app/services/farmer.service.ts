import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Farmer } from '../models/farmer.module';

@Injectable({ providedIn: 'root' })
export class FarmerService {
  private baseUrl = 'http://localhost:8080/api/farmers';

  constructor(private http: HttpClient) {}

  // GET all farmers
  getAll(): Observable<Farmer[]> {
    return this.http.get<Farmer[]>(this.baseUrl);
  }

  // GET farmer by ID
  get(id: number): Observable<Farmer> {
    return this.http.get<Farmer>(`${this.baseUrl}/${id}`);
  }

  // SEARCH farmers by village
  search(village: string): Observable<Farmer[]> {
    if (!village.trim()) {
      return this.getAll();
    }
    return this.http.get<Farmer[]>(`${this.baseUrl}/search`, { params: { village } });
  }

  // CREATE new farmer
  create(farmer: Partial<Farmer>): Observable<Farmer> {
    // Partial to allow omitting auto-generated farmerId and optional fields
    return this.http.post<Farmer>(this.baseUrl, farmer);
  }

  // UPDATE existing farmer
  update(id: number, farmer: Partial<Farmer>): Observable<Farmer> {
    return this.http.put<Farmer>(`${this.baseUrl}/${id}`, farmer);
  }

  // DELETE farmer
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}