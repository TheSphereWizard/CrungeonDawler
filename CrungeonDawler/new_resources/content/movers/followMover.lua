function GetMove (position,target,vx,vy,speed,time,fDistance) 
  local px = position:getX()
  local py = position:getY()
  local tx = target:getX()
  local ty = target:getY()
  local dx = px-tx
  local dy = py-ty
  local d2 = dx*dx+dy*dy
  if d2 > fDistance*fDistance then
    return dx*speed/math.sqrt(d2),dy*speed/math.sqrt(d2)
  else
    return 1,1
  end  
end