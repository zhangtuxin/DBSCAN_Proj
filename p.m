a = importdata('result.txt');
m=size(a,1);
for i=1:1:m
    if a(i,3)==1
        plot(a(i,1),a(i,2),'r.');
    elseif a(i,3)==2
        plot(a(i,1),a(i,2),'b.');
    else
        plot(a(i,1),a(i,2),'m.');   
    end
    hold on;
end
grid on;