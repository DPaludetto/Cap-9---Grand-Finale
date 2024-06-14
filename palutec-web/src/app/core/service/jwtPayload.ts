export interface IJwtPayload {
  username: string;
  userId: string;
  mail: string;
  fullName: string;
  groupName: string;
  groupId: string;
  principalAreaName: string;
  principalAreaId: string;
  scope: string [];
  authorities?: string [];
  exp: number;
  jti: string;
}
