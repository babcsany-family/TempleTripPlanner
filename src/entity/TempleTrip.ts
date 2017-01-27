/**
 * Created by peter on 2017. 01. 20..
 */

import { Table, PrimaryColumn, Column } from "typeorm";

@Table()
export class TempleTrip {
  @PrimaryColumn()
  uuid: string;

  @Column()
  from: Date;

  @Column()
  to: Date;

  @Column()
  patrons: number;
}
